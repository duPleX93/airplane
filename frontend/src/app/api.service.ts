import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Airline } from './models/airline';
import { CityExtremes } from './models/city-extremes';
import { Flight } from './models/flight';
import { RouteResult } from './models/route-result';

@Injectable({ providedIn: 'root' })
export class ApiService {
  private readonly base = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  getAirlines(): Observable<Airline[]> {
    return this.http.get<Airline[]>(`${this.base}/airlines`);
  }

  getFlights(): Observable<Flight[]> {
    return this.http.get<Flight[]>(`${this.base}/flights`);
  }

  searchFlights(from?: string | null, to?: string | null): Observable<Flight[]> {
    let params = new HttpParams();
    if (from != null && from !== '') params = params.set('from', from);
    if (to != null && to !== '') params = params.set('to', to);
    return this.http.get<Flight[]>(`${this.base}/flights/search`, { params });
  }

  getCityExtremes(): Observable<CityExtremes> {
    return this.http.get<CityExtremes>(`${this.base}/cities/extremes`);
  }

  getRoutesPerAirline(): Observable<RouteResult[]> {
    return this.http.get<RouteResult[]>(`${this.base}/routes/per-airline`);
  }

  getBestRoute(): Observable<RouteResult> {
    return this.http.get<RouteResult>(`${this.base}/routes/best`);
  }
}
