import { IProductionLine } from 'app/shared/model/production-line.model';
import { IFactory } from 'app/shared/model/factory.model';

export interface IProductionLineGroup {
  id?: number;
  name?: string | null;
  description?: string | null;
  productionLines?: IProductionLine[] | null;
  factory?: IFactory | null;
}

export const defaultValue: Readonly<IProductionLineGroup> = {};
