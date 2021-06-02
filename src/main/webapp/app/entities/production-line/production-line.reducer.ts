import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IProductionLine, defaultValue } from 'app/shared/model/production-line.model';

export const ACTION_TYPES = {
  FETCH_PRODUCTIONLINE_LIST: 'productionLine/FETCH_PRODUCTIONLINE_LIST',
  FETCH_PRODUCTIONLINE: 'productionLine/FETCH_PRODUCTIONLINE',
  CREATE_PRODUCTIONLINE: 'productionLine/CREATE_PRODUCTIONLINE',
  UPDATE_PRODUCTIONLINE: 'productionLine/UPDATE_PRODUCTIONLINE',
  PARTIAL_UPDATE_PRODUCTIONLINE: 'productionLine/PARTIAL_UPDATE_PRODUCTIONLINE',
  DELETE_PRODUCTIONLINE: 'productionLine/DELETE_PRODUCTIONLINE',
  RESET: 'productionLine/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IProductionLine>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type ProductionLineState = Readonly<typeof initialState>;

// Reducer

export default (state: ProductionLineState = initialState, action): ProductionLineState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PRODUCTIONLINE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PRODUCTIONLINE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_PRODUCTIONLINE):
    case REQUEST(ACTION_TYPES.UPDATE_PRODUCTIONLINE):
    case REQUEST(ACTION_TYPES.DELETE_PRODUCTIONLINE):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_PRODUCTIONLINE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_PRODUCTIONLINE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PRODUCTIONLINE):
    case FAILURE(ACTION_TYPES.CREATE_PRODUCTIONLINE):
    case FAILURE(ACTION_TYPES.UPDATE_PRODUCTIONLINE):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_PRODUCTIONLINE):
    case FAILURE(ACTION_TYPES.DELETE_PRODUCTIONLINE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_PRODUCTIONLINE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_PRODUCTIONLINE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_PRODUCTIONLINE):
    case SUCCESS(ACTION_TYPES.UPDATE_PRODUCTIONLINE):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_PRODUCTIONLINE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_PRODUCTIONLINE):
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

const apiUrl = 'api/production-lines';

// Actions

export const getEntities: ICrudGetAllAction<IProductionLine> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_PRODUCTIONLINE_LIST,
  payload: axios.get<IProductionLine>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IProductionLine> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PRODUCTIONLINE,
    payload: axios.get<IProductionLine>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IProductionLine> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PRODUCTIONLINE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IProductionLine> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PRODUCTIONLINE,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IProductionLine> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_PRODUCTIONLINE,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IProductionLine> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PRODUCTIONLINE,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
