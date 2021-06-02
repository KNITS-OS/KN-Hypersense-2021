import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IMetricData, defaultValue } from 'app/shared/model/metric-data.model';

export const ACTION_TYPES = {
  FETCH_METRICDATA_LIST: 'metricData/FETCH_METRICDATA_LIST',
  FETCH_METRICDATA: 'metricData/FETCH_METRICDATA',
  CREATE_METRICDATA: 'metricData/CREATE_METRICDATA',
  UPDATE_METRICDATA: 'metricData/UPDATE_METRICDATA',
  PARTIAL_UPDATE_METRICDATA: 'metricData/PARTIAL_UPDATE_METRICDATA',
  DELETE_METRICDATA: 'metricData/DELETE_METRICDATA',
  RESET: 'metricData/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IMetricData>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type MetricDataState = Readonly<typeof initialState>;

// Reducer

export default (state: MetricDataState = initialState, action): MetricDataState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_METRICDATA_LIST):
    case REQUEST(ACTION_TYPES.FETCH_METRICDATA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_METRICDATA):
    case REQUEST(ACTION_TYPES.UPDATE_METRICDATA):
    case REQUEST(ACTION_TYPES.DELETE_METRICDATA):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_METRICDATA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_METRICDATA_LIST):
    case FAILURE(ACTION_TYPES.FETCH_METRICDATA):
    case FAILURE(ACTION_TYPES.CREATE_METRICDATA):
    case FAILURE(ACTION_TYPES.UPDATE_METRICDATA):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_METRICDATA):
    case FAILURE(ACTION_TYPES.DELETE_METRICDATA):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_METRICDATA_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_METRICDATA):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_METRICDATA):
    case SUCCESS(ACTION_TYPES.UPDATE_METRICDATA):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_METRICDATA):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_METRICDATA):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/metric-data';

// Actions

export const getEntities: ICrudGetAllAction<IMetricData> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_METRICDATA_LIST,
  payload: axios.get<IMetricData>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IMetricData> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_METRICDATA,
    payload: axios.get<IMetricData>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IMetricData> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_METRICDATA,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IMetricData> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_METRICDATA,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IMetricData> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_METRICDATA,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IMetricData> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_METRICDATA,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
