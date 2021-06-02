export interface IUserProfile {
  id?: number;
  address?: string | null;
  city?: string | null;
  country?: string | null;
  postalCode?: string | null;
}

export const defaultValue: Readonly<IUserProfile> = {};
