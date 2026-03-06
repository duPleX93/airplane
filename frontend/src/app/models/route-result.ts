import { RouteSegment } from './route-segment';

export interface RouteResult {
  message?: string;
  airlineId?: string;
  airlineName?: string;
  fromCityName: string;
  toCityName: string;
  totalDistanceKm?: number;
  totalDurationFormatted?: string;
  totalElapsedMinutes?: number;
  transfers?: number;
  segments?: RouteSegment[];
}
