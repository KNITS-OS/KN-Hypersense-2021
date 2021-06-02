import { IMetricData } from 'app/shared/model/metric-data.model';

export interface IMetric {
  id?: number;
  thingUuid?: string | null;
  metrics?: IMetricData[] | null;
}

export const defaultValue: Readonly<IMetric> = {};
