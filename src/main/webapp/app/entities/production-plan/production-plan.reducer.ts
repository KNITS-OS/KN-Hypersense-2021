import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IProductionPlan, defaultValue } from 'app/shared/model/production-plan.model';

export const ACTION_TYPES = {
  FETCH_PRODUCTIONPLAN_LIST: 'productionPlan/FETCH_PRODUCTIONPLAN_LIST',
  FETCH_PRODUCTIONPLAN: 'productionPlan/FETCH_PRODUCTIONPLAN',
  CREATE_PRODUCTIONPLAN: 'productionPlan/CREATE_PRODUCTIONPLAN',
  UPDATE_PRODUCTIONPLAN: 'productionPlan/UPDATE_PRODUCTIONPLAN',
  PARTIAL_UPDATE_PRODUCTIONPLAN: 'productionPlan/PARTIAL_UPDATE_PRODUCTIONPLAN',
  DELETE_PRODUCTIONPLAN: 'productionPlan/DELETE_PRODUCTIONPLAN',
  RESET: 'productionPlan/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IProductionPlan>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type ProductionPlanState = Readonly<typeof initialState>;

// Reducer

export default (state: ProductionPlanState = initialState, action): ProductionPlanState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PRODUCTIONPLAN_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PRODUCTIONPLAN):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_PRODUCTIONPLAN):
    case REQUEST(ACTION_TYPES.UPDATE_PRODUCTIONPLAN):
    case REQUEST(ACTION_TYPES.DELETE_PRODUCTIONPLAN):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_PRODUCTIONPLAN):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_PRODUCTIONPLAN_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PRODUCTIONPLAN):
    case FAILURE(ACTION_TYPES.CREATE_PRODUCTIONPLAN):
    case FAILURE(ACTION_TYPES.UPDATE_PRODUCTIONPLAN):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_PRODUCTIONPLAN):
    case FAILURE(ACTION_TYPES.DELETE_PRODUCTIONPLAN):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_PRODUCTIONPLAN_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_PRODUCTIONPLAN):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_PRODUCTIONPLAN):
    case SUCCESS(ACTION_TYPES.UPDATE_PRODUCTIONPLAN):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_PRODUCTIONPLAN):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_PRODUCTIONPLAN):
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

const apiUrl = 'api/production-plans';

// Actions

export const getEntities: ICrudGetAllAction<IProductionPlan> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_PRODUCTIONPLAN_LIST,
  payload: axios.get<IProductionPlan>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IProductionPlan> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PRODUCTIONPLAN,
    payload: axios.get<IProductionPlan>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IProductionPlan> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PRODUCTIONPLAN,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IProductionPlan> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PRODUCTIONPLAN,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IProductionPlan> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_PRODUCTIONPLAN,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IProductionPlan> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PRODUCTIONPLAN,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
