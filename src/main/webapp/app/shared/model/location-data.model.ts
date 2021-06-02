export interface ILocationData {
  id?: number;
  floor?: string | null;
  room?: string | null;
  additionalInfo?: string | null;
}

export const defaultValue: Readonly<ILocationData> = {};
