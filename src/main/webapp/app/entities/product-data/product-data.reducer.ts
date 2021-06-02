import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IProductData, defaultValue } from 'app/shared/model/product-data.model';

export const ACTION_TYPES = {
  FETCH_PRODUCTDATA_LIST: 'productData/FETCH_PRODUCTDATA_LIST',
  FETCH_PRODUCTDATA: 'productData/FETCH_PRODUCTDATA',
  CREATE_PRODUCTDATA: 'productData/CREATE_PRODUCTDATA',
  UPDATE_PRODUCTDATA: 'productData/UPDATE_PRODUCTDATA',
  PARTIAL_UPDATE_PRODUCTDATA: 'productData/PARTIAL_UPDATE_PRODUCTDATA',
  DELETE_PRODUCTDATA: 'productData/DELETE_PRODUCTDATA',
  RESET: 'productData/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IProductData>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type ProductDataState = Readonly<typeof initialState>;

// Reducer

export default (state: ProductDataState = initialState, action): ProductDataState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PRODUCTDATA_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PRODUCTDATA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_PRODUCTDATA):
    case REQUEST(ACTION_TYPES.UPDATE_PRODUCTDATA):
    case REQUEST(ACTION_TYPES.DELETE_PRODUCTDATA):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_PRODUCTDATA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_PRODUCTDATA_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PRODUCTDATA):
    case FAILURE(ACTION_TYPES.CREATE_PRODUCTDATA):
    case FAILURE(ACTION_TYPES.UPDATE_PRODUCTDATA):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_PRODUCTDATA):
    case FAILURE(ACTION_TYPES.DELETE_PRODUCTDATA):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_PRODUCTDATA_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_PRODUCTDATA):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_PRODUCTDATA):
    case SUCCESS(ACTION_TYPES.UPDATE_PRODUCTDATA):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_PRODUCTDATA):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_PRODUCTDATA):
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

const apiUrl = 'api/product-data';

// Actions

export const getEntities: ICrudGetAllAction<IProductData> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_PRODUCTDATA_LIST,
  payload: axios.get<IProductData>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IProductData> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PRODUCTDATA,
    payload: axios.get<IProductData>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IProductData> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PRODUCTDATA,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IProductData> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PRODUCTDATA,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IProductData> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_PRODUCTDATA,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IProductData> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PRODUCTDATA,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
