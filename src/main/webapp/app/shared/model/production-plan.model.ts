import dayjs from 'dayjs';
import { IProductData } from 'app/shared/model/product-data.model';

export interface IProductionPlan {
  id?: number;
  dueDate?: string | null;
  qty?: number | null;
  name?: string | null;
  productData?: IProductData[] | null;
}

export const defaultValue: Readonly<IProductionPlan> = {};
