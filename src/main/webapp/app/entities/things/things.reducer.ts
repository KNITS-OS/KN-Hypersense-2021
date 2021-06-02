import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IThings, defaultValue } from 'app/shared/model/things.model';

export const ACTION_TYPES = {
  FETCH_THINGS_LIST: 'things/FETCH_THINGS_LIST',
  FETCH_THINGS: 'things/FETCH_THINGS',
  CREATE_THINGS: 'things/CREATE_THINGS',
  UPDATE_THINGS: 'things/UPDATE_THINGS',
  PARTIAL_UPDATE_THINGS: 'things/PARTIAL_UPDATE_THINGS',
  DELETE_THINGS: 'things/DELETE_THINGS',
  RESET: 'things/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IThings>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type ThingsState = Readonly<typeof initialState>;

// Reducer

export default (state: ThingsState = initialState, action): ThingsState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_THINGS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_THINGS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_THINGS):
    case REQUEST(ACTION_TYPES.UPDATE_THINGS):
    case REQUEST(ACTION_TYPES.DELETE_THINGS):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_THINGS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_THINGS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_THINGS):
    case FAILURE(ACTION_TYPES.CREATE_THINGS):
    case FAILURE(ACTION_TYPES.UPDATE_THINGS):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_THINGS):
    case FAILURE(ACTION_TYPES.DELETE_THINGS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_THINGS_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_THINGS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_THINGS):
    case SUCCESS(ACTION_TYPES.UPDATE_THINGS):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_THINGS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_THINGS):
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

const apiUrl = 'api/things';

// Actions

export const getEntities: ICrudGetAllAction<IThings> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_THINGS_LIST,
  payload: axios.get<IThings>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IThings> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_THINGS,
    payload: axios.get<IThings>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IThings> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_THINGS,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IThings> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_THINGS,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IThings> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_THINGS,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IThings> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_THINGS,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
