import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Metric from './metric';
import MetricDetail from './metric-detail';
import MetricUpdate from './metric-update';
import MetricDeleteDialog from './metric-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MetricUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MetricUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MetricDetail} />
      <ErrorBoundaryRoute path={match.url} component={Metric} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={MetricDeleteDialog} />
  </>
);

export default Routes;
