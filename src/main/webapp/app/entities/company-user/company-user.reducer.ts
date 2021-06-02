import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ICompanyUser, defaultValue } from 'app/shared/model/company-user.model';

export const ACTION_TYPES = {
  FETCH_COMPANYUSER_LIST: 'companyUser/FETCH_COMPANYUSER_LIST',
  FETCH_COMPANYUSER: 'companyUser/FETCH_COMPANYUSER',
  CREATE_COMPANYUSER: 'companyUser/CREATE_COMPANYUSER',
  UPDATE_COMPANYUSER: 'companyUser/UPDATE_COMPANYUSER',
  PARTIAL_UPDATE_COMPANYUSER: 'companyUser/PARTIAL_UPDATE_COMPANYUSER',
  DELETE_COMPANYUSER: 'companyUser/DELETE_COMPANYUSER',
  RESET: 'companyUser/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICompanyUser>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type CompanyUserState = Readonly<typeof initialState>;

// Reducer

export default (state: CompanyUserState = initialState, action): CompanyUserState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_COMPANYUSER_LIST):
    case REQUEST(ACTION_TYPES.FETCH_COMPANYUSER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_COMPANYUSER):
    case REQUEST(ACTION_TYPES.UPDATE_COMPANYUSER):
    case REQUEST(ACTION_TYPES.DELETE_COMPANYUSER):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_COMPANYUSER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_COMPANYUSER_LIST):
    case FAILURE(ACTION_TYPES.FETCH_COMPANYUSER):
    case FAILURE(ACTION_TYPES.CREATE_COMPANYUSER):
    case FAILURE(ACTION_TYPES.UPDATE_COMPANYUSER):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_COMPANYUSER):
    case FAILURE(ACTION_TYPES.DELETE_COMPANYUSER):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_COMPANYUSER_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_COMPANYUSER):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_COMPANYUSER):
    case SUCCESS(ACTION_TYPES.UPDATE_COMPANYUSER):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_COMPANYUSER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_COMPANYUSER):
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

const apiUrl = 'api/company-users';

// Actions

export const getEntities: ICrudGetAllAction<ICompanyUser> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_COMPANYUSER_LIST,
  payload: axios.get<ICompanyUser>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<ICompanyUser> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_COMPANYUSER,
    payload: axios.get<ICompanyUser>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ICompanyUser> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_COMPANYUSER,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ICompanyUser> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_COMPANYUSER,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<ICompanyUser> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_COMPANYUSER,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICompanyUser> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_COMPANYUSER,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
