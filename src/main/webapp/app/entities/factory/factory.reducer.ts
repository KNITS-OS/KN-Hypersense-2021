import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IFactory, defaultValue } from 'app/shared/model/factory.model';

export const ACTION_TYPES = {
  FETCH_FACTORY_LIST: 'factory/FETCH_FACTORY_LIST',
  FETCH_FACTORY: 'factory/FETCH_FACTORY',
  CREATE_FACTORY: 'factory/CREATE_FACTORY',
  UPDATE_FACTORY: 'factory/UPDATE_FACTORY',
  PARTIAL_UPDATE_FACTORY: 'factory/PARTIAL_UPDATE_FACTORY',
  DELETE_FACTORY: 'factory/DELETE_FACTORY',
  RESET: 'factory/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IFactory>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type FactoryState = Readonly<typeof initialState>;

// Reducer

export default (state: FactoryState = initialState, action): FactoryState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_FACTORY_LIST):
    case REQUEST(ACTION_TYPES.FETCH_FACTORY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_FACTORY):
    case REQUEST(ACTION_TYPES.UPDATE_FACTORY):
    case REQUEST(ACTION_TYPES.DELETE_FACTORY):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_FACTORY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_FACTORY_LIST):
    case FAILURE(ACTION_TYPES.FETCH_FACTORY):
    case FAILURE(ACTION_TYPES.CREATE_FACTORY):
    case FAILURE(ACTION_TYPES.UPDATE_FACTORY):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_FACTORY):
    case FAILURE(ACTION_TYPES.DELETE_FACTORY):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_FACTORY_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_FACTORY):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_FACTORY):
    case SUCCESS(ACTION_TYPES.UPDATE_FACTORY):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_FACTORY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_FACTORY):
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

const apiUrl = 'api/factories';

// Actions

export const getEntities: ICrudGetAllAction<IFactory> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_FACTORY_LIST,
  payload: axios.get<IFactory>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IFactory> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_FACTORY,
    payload: axios.get<IFactory>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IFactory> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_FACTORY,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IFactory> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_FACTORY,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IFactory> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_FACTORY,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IFactory> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_FACTORY,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
