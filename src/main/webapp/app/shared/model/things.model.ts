import { IProductionLine } from 'app/shared/model/production-line.model';

export interface IThings {
  id?: number;
  thingUuid?: string | null;
  productionLine?: IProductionLine | null;
}

export const defaultValue: Readonly<IThings> = {};
