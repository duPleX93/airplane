import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { CityExtremes } from './models/city-extremes';
import { RouteResult } from './models/route-result';

@Injectable({ providedIn: 'root' })
export class ApiService {
  private readonly base = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  getCityExtremes(): Observable<CityExtremes> {
    return this.http.get<CityExtremes>(`${this.base}/cities/extremes`).pipe(
      catchError((err) => throwError(() => new Error(this.getErrorMessage(err)))),
    );
  }

  getRoutesPerAirline(): Observable<RouteResult[]> {
    return this.http.get<RouteResult[]>(`${this.base}/routes/per-airline`).pipe(
      catchError((err) => throwError(() => new Error(this.getErrorMessage(err)))),
    );
  }

  getBestRoute(): Observable<RouteResult> {
    return this.http.get<RouteResult>(`${this.base}/routes/best`).pipe(
      catchError((err) => throwError(() => new Error(this.getErrorMessage(err)))),
    );
  }

  /**
   * HTTP hiba átalakítása felhasználóbarát szöveggé.
   */
  private getErrorMessage(err: HttpErrorResponse): string {
    if (err.error instanceof ErrorEvent) {
      return `Hálózati hiba: ${err.error.message}`;
    }
    if (err.status === 0) {
      return 'Nem sikerült kapcsolódni a szerverhez. Fut a backend (port 8080)?';
    }
    const body = err.error;
    const message =
      typeof body === 'object' && body != null && 'message' in body
        ? String((body as { message: unknown }).message)
        : typeof body === 'string'
          ? body
          : err.message || null;
    if (message) {
      return message;
    }
    switch (err.status) {
      case 404:
        return 'A kért erőforrás nem található.';
      case 500:
        return 'Szerverhiba. Próbáld később.';
      case 502:
      case 503:
        return 'A szolgáltatás jelenleg nem elérhető.';
      default:
        return err.statusText || `Hiba (${err.status})`;
    }
  }
}
