import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ProductData from './product-data';
import ProductDataDetail from './product-data-detail';
import ProductDataUpdate from './product-data-update';
import ProductDataDeleteDialog from './product-data-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ProductDataUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ProductDataUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ProductDataDetail} />
      <ErrorBoundaryRoute path={match.url} component={ProductData} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ProductDataDeleteDialog} />
  </>
);

export default Routes;
