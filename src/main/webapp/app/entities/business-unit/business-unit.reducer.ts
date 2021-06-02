import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IBusinessUnit, defaultValue } from 'app/shared/model/business-unit.model';

export const ACTION_TYPES = {
  FETCH_BUSINESSUNIT_LIST: 'businessUnit/FETCH_BUSINESSUNIT_LIST',
  FETCH_BUSINESSUNIT: 'businessUnit/FETCH_BUSINESSUNIT',
  CREATE_BUSINESSUNIT: 'businessUnit/CREATE_BUSINESSUNIT',
  UPDATE_BUSINESSUNIT: 'businessUnit/UPDATE_BUSINESSUNIT',
  PARTIAL_UPDATE_BUSINESSUNIT: 'businessUnit/PARTIAL_UPDATE_BUSINESSUNIT',
  DELETE_BUSINESSUNIT: 'businessUnit/DELETE_BUSINESSUNIT',
  RESET: 'businessUnit/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IBusinessUnit>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type BusinessUnitState = Readonly<typeof initialState>;

// Reducer

export default (state: BusinessUnitState = initialState, action): BusinessUnitState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_BUSINESSUNIT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_BUSINESSUNIT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_BUSINESSUNIT):
    case REQUEST(ACTION_TYPES.UPDATE_BUSINESSUNIT):
    case REQUEST(ACTION_TYPES.DELETE_BUSINESSUNIT):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_BUSINESSUNIT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_BUSINESSUNIT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_BUSINESSUNIT):
    case FAILURE(ACTION_TYPES.CREATE_BUSINESSUNIT):
    case FAILURE(ACTION_TYPES.UPDATE_BUSINESSUNIT):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_BUSINESSUNIT):
    case FAILURE(ACTION_TYPES.DELETE_BUSINESSUNIT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_BUSINESSUNIT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_BUSINESSUNIT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_BUSINESSUNIT):
    case SUCCESS(ACTION_TYPES.UPDATE_BUSINESSUNIT):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_BUSINESSUNIT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_BUSINESSUNIT):
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

const apiUrl = 'api/business-units';

// Actions

export const getEntities: ICrudGetAllAction<IBusinessUnit> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_BUSINESSUNIT_LIST,
  payload: axios.get<IBusinessUnit>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IBusinessUnit> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_BUSINESSUNIT,
    payload: axios.get<IBusinessUnit>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IBusinessUnit> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_BUSINESSUNIT,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IBusinessUnit> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_BUSINESSUNIT,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IBusinessUnit> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_BUSINESSUNIT,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IBusinessUnit> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_BUSINESSUNIT,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
