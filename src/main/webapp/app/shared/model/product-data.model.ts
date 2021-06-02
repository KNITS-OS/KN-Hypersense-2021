import { IProductionPlan } from 'app/shared/model/production-plan.model';

export interface IProductData {
  id?: number;
  name?: string | null;
  scrapedQty?: number | null;
  pendingQty?: number | null;
  rejectedQty?: number | null;
  completedQty?: number | null;
  productionPlan?: IProductionPlan | null;
}

export const defaultValue: Readonly<IProductData> = {};
