export interface Flight {
  id: string;
  distanceKm: number;
  durationMinutes: number;
  fromCityId: string;
  fromCityName: string;
  toCityId: string;
  toCityName: string;
  airlineId: string;
  airlineName: string;
}
