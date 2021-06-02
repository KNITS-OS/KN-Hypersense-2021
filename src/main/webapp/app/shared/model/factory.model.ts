import { IProductionLineGroup } from 'app/shared/model/production-line-group.model';
import { IBusinessUnit } from 'app/shared/model/business-unit.model';

export interface IFactory {
  id?: number;
  name?: string | null;
  description?: string | null;
  location?: string | null;
  type?: string | null;
  productionLineGroups?: IProductionLineGroup[] | null;
  businessUnits?: IBusinessUnit[] | null;
}

export const defaultValue: Readonly<IFactory> = {};
