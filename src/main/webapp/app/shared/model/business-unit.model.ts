import { ICompanyUser } from 'app/shared/model/company-user.model';
import { IFactory } from 'app/shared/model/factory.model';

export interface IBusinessUnit {
  id?: number;
  name?: string | null;
  description?: string | null;
  users?: ICompanyUser[] | null;
  factory?: IFactory | null;
}

export const defaultValue: Readonly<IBusinessUnit> = {};
