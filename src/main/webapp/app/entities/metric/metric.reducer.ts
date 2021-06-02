import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IMetric, defaultValue } from 'app/shared/model/metric.model';

export const ACTION_TYPES = {
  FETCH_METRIC_LIST: 'metric/FETCH_METRIC_LIST',
  FETCH_METRIC: 'metric/FETCH_METRIC',
  CREATE_METRIC: 'metric/CREATE_METRIC',
  UPDATE_METRIC: 'metric/UPDATE_METRIC',
  PARTIAL_UPDATE_METRIC: 'metric/PARTIAL_UPDATE_METRIC',
  DELETE_METRIC: 'metric/DELETE_METRIC',
  RESET: 'metric/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IMetric>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type MetricState = Readonly<typeof initialState>;

// Reducer

export default (state: MetricState = initialState, action): MetricState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_METRIC_LIST):
    case REQUEST(ACTION_TYPES.FETCH_METRIC):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_METRIC):
    case REQUEST(ACTION_TYPES.UPDATE_METRIC):
    case REQUEST(ACTION_TYPES.DELETE_METRIC):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_METRIC):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_METRIC_LIST):
    case FAILURE(ACTION_TYPES.FETCH_METRIC):
    case FAILURE(ACTION_TYPES.CREATE_METRIC):
    case FAILURE(ACTION_TYPES.UPDATE_METRIC):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_METRIC):
    case FAILURE(ACTION_TYPES.DELETE_METRIC):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_METRIC_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_METRIC):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_METRIC):
    case SUCCESS(ACTION_TYPES.UPDATE_METRIC):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_METRIC):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_METRIC):
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

const apiUrl = 'api/metrics';

// Actions

export const getEntities: ICrudGetAllAction<IMetric> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_METRIC_LIST,
  payload: axios.get<IMetric>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IMetric> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_METRIC,
    payload: axios.get<IMetric>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IMetric> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_METRIC,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IMetric> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_METRIC,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IMetric> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_METRIC,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IMetric> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_METRIC,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
