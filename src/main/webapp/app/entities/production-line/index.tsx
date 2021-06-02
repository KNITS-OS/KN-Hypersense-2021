import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ProductionLine from './production-line';
import ProductionLineDetail from './production-line-detail';
import ProductionLineUpdate from './production-line-update';
import ProductionLineDeleteDialog from './production-line-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ProductionLineUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ProductionLineUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ProductionLineDetail} />
      <ErrorBoundaryRoute path={match.url} component={ProductionLine} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ProductionLineDeleteDialog} />
  </>
);

export default Routes;
