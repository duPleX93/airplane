export interface RouteSegment {
  flightId: string;
  fromCityName: string;
  toCityName: string;
  airlineName: string;
  distanceKm: number;
  durationFormatted: string;
  durationMinutes: number;
  departureTimeFormatted: string;
  arrivalTimeFormatted: string;
  waitMinutes: number | null;
}
