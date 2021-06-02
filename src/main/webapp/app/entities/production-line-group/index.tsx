import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ProductionLineGroup from './production-line-group';
import ProductionLineGroupDetail from './production-line-group-detail';
import ProductionLineGroupUpdate from './production-line-group-update';
import ProductionLineGroupDeleteDialog from './production-line-group-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ProductionLineGroupUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ProductionLineGroupUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ProductionLineGroupDetail} />
      <ErrorBoundaryRoute path={match.url} component={ProductionLineGroup} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ProductionLineGroupDeleteDialog} />
  </>
);

export default Routes;
