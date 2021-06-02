import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IProductionLineGroup, defaultValue } from 'app/shared/model/production-line-group.model';

export const ACTION_TYPES = {
  FETCH_PRODUCTIONLINEGROUP_LIST: 'productionLineGroup/FETCH_PRODUCTIONLINEGROUP_LIST',
  FETCH_PRODUCTIONLINEGROUP: 'productionLineGroup/FETCH_PRODUCTIONLINEGROUP',
  CREATE_PRODUCTIONLINEGROUP: 'productionLineGroup/CREATE_PRODUCTIONLINEGROUP',
  UPDATE_PRODUCTIONLINEGROUP: 'productionLineGroup/UPDATE_PRODUCTIONLINEGROUP',
  PARTIAL_UPDATE_PRODUCTIONLINEGROUP: 'productionLineGroup/PARTIAL_UPDATE_PRODUCTIONLINEGROUP',
  DELETE_PRODUCTIONLINEGROUP: 'productionLineGroup/DELETE_PRODUCTIONLINEGROUP',
  RESET: 'productionLineGroup/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IProductionLineGroup>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type ProductionLineGroupState = Readonly<typeof initialState>;

// Reducer

export default (state: ProductionLineGroupState = initialState, action): ProductionLineGroupState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PRODUCTIONLINEGROUP_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PRODUCTIONLINEGROUP):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_PRODUCTIONLINEGROUP):
    case REQUEST(ACTION_TYPES.UPDATE_PRODUCTIONLINEGROUP):
    case REQUEST(ACTION_TYPES.DELETE_PRODUCTIONLINEGROUP):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_PRODUCTIONLINEGROUP):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_PRODUCTIONLINEGROUP_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PRODUCTIONLINEGROUP):
    case FAILURE(ACTION_TYPES.CREATE_PRODUCTIONLINEGROUP):
    case FAILURE(ACTION_TYPES.UPDATE_PRODUCTIONLINEGROUP):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_PRODUCTIONLINEGROUP):
    case FAILURE(ACTION_TYPES.DELETE_PRODUCTIONLINEGROUP):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_PRODUCTIONLINEGROUP_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_PRODUCTIONLINEGROUP):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_PRODUCTIONLINEGROUP):
    case SUCCESS(ACTION_TYPES.UPDATE_PRODUCTIONLINEGROUP):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_PRODUCTIONLINEGROUP):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_PRODUCTIONLINEGROUP):
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

const apiUrl = 'api/production-line-groups';

// Actions

export const getEntities: ICrudGetAllAction<IProductionLineGroup> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_PRODUCTIONLINEGROUP_LIST,
  payload: axios.get<IProductionLineGroup>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IProductionLineGroup> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PRODUCTIONLINEGROUP,
    payload: axios.get<IProductionLineGroup>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IProductionLineGroup> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PRODUCTIONLINEGROUP,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IProductionLineGroup> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PRODUCTIONLINEGROUP,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IProductionLineGroup> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_PRODUCTIONLINEGROUP,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IProductionLineGroup> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PRODUCTIONLINEGROUP,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
