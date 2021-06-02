export interface IStatus {
  id?: number;
  name?: string | null;
  description?: string | null;
}

export const defaultValue: Readonly<IStatus> = {};
