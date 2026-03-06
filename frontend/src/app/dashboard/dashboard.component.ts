import { ChangeDetectionStrategy, Component, OnInit, signal } from '@angular/core';
import { DecimalPipe } from '@angular/common';
import { forkJoin } from 'rxjs';
import { ApiService } from '../api.service';
import { CityExtremes } from '../models/city-extremes';
import { RouteResult } from '../models/route-result';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [DecimalPipe],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class DashboardComponent implements OnInit {
  readonly extremes = signal<CityExtremes | null>(null);
  readonly perAirlineRoutes = signal<RouteResult[]>([]);
  readonly bestRoute = signal<RouteResult | null>(null);
  readonly loading = signal(true);
  readonly error = signal<string | null>(null);

  constructor(private api: ApiService) {}

  ngOnInit(): void {
    this.load();
  }

  load(): void {
    this.loading.set(true);
    this.error.set(null);
    forkJoin({
      extremes: this.api.getCityExtremes(),
      perAirline: this.api.getRoutesPerAirline(),
      best: this.api.getBestRoute(),
    }).subscribe({
      next: ({ extremes: e, perAirline: r, best: b }) => {
        this.extremes.set(e);
        this.perAirlineRoutes.set(r);
        this.bestRoute.set(b);
      },
      error: (err: unknown) => {
        const message =
          err instanceof Error
            ? err.message
            : typeof err === 'object' && err != null && 'message' in (err as object)
              ? String((err as { message: unknown }).message)
              : 'Hiba történt';
        this.error.set(message);
        this.loading.set(false);
      },
      complete: () => this.loading.set(false),
    });
  }
}
