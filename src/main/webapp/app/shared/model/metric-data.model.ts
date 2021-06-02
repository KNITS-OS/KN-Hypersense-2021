import dayjs from 'dayjs';
import { IStatus } from 'app/shared/model/status.model';
import { IMetric } from 'app/shared/model/metric.model';

export interface IMetricData {
  id?: number;
  timeStamp?: string | null;
  measureValue?: string | null;
  name?: string | null;
  status?: IStatus | null;
  metric?: IMetric | null;
}

export const defaultValue: Readonly<IMetricData> = {};
