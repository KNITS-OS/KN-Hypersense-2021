import { ILocationData } from 'app/shared/model/location-data.model';
import { IThings } from 'app/shared/model/things.model';
import { IState } from 'app/shared/model/state.model';
import { IProductionLineGroup } from 'app/shared/model/production-line-group.model';

export interface IProductionLine {
  id?: number;
  name?: string | null;
  description?: string | null;
  locationData?: ILocationData | null;
  things?: IThings[] | null;
  states?: IState[] | null;
  productionLineGroup?: IProductionLineGroup | null;
}

export const defaultValue: Readonly<IProductionLine> = {};
