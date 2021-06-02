import { IProductionLine } from 'app/shared/model/production-line.model';

export interface IState {
  id?: number;
  name?: string | null;
  description?: string | null;
  productionLine?: IProductionLine | null;
}

export const defaultValue: Readonly<IState> = {};
