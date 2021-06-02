import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ILocationData, defaultValue } from 'app/shared/model/location-data.model';

export const ACTION_TYPES = {
  FETCH_LOCATIONDATA_LIST: 'locationData/FETCH_LOCATIONDATA_LIST',
  FETCH_LOCATIONDATA: 'locationData/FETCH_LOCATIONDATA',
  CREATE_LOCATIONDATA: 'locationData/CREATE_LOCATIONDATA',
  UPDATE_LOCATIONDATA: 'locationData/UPDATE_LOCATIONDATA',
  PARTIAL_UPDATE_LOCATIONDATA: 'locationData/PARTIAL_UPDATE_LOCATIONDATA',
  DELETE_LOCATIONDATA: 'locationData/DELETE_LOCATIONDATA',
  RESET: 'locationData/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ILocationData>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type LocationDataState = Readonly<typeof initialState>;

// Reducer

export default (state: LocationDataState = initialState, action): LocationDataState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_LOCATIONDATA_LIST):
    case REQUEST(ACTION_TYPES.FETCH_LOCATIONDATA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_LOCATIONDATA):
    case REQUEST(ACTION_TYPES.UPDATE_LOCATIONDATA):
    case REQUEST(ACTION_TYPES.DELETE_LOCATIONDATA):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_LOCATIONDATA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_LOCATIONDATA_LIST):
    case FAILURE(ACTION_TYPES.FETCH_LOCATIONDATA):
    case FAILURE(ACTION_TYPES.CREATE_LOCATIONDATA):
    case FAILURE(ACTION_TYPES.UPDATE_LOCATIONDATA):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_LOCATIONDATA):
    case FAILURE(ACTION_TYPES.DELETE_LOCATIONDATA):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_LOCATIONDATA_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_LOCATIONDATA):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_LOCATIONDATA):
    case SUCCESS(ACTION_TYPES.UPDATE_LOCATIONDATA):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_LOCATIONDATA):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_LOCATIONDATA):
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

const apiUrl = 'api/location-data';

// Actions

export const getEntities: ICrudGetAllAction<ILocationData> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_LOCATIONDATA_LIST,
  payload: axios.get<ILocationData>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<ILocationData> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_LOCATIONDATA,
    payload: axios.get<ILocationData>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ILocationData> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_LOCATIONDATA,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ILocationData> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_LOCATIONDATA,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<ILocationData> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_LOCATIONDATA,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ILocationData> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_LOCATIONDATA,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
