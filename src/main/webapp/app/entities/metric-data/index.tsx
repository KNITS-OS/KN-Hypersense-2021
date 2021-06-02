import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import MetricData from './metric-data';
import MetricDataDetail from './metric-data-detail';
import MetricDataUpdate from './metric-data-update';
import MetricDataDeleteDialog from './metric-data-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MetricDataUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MetricDataUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MetricDataDetail} />
      <ErrorBoundaryRoute path={match.url} component={MetricData} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={MetricDataDeleteDialog} />
  </>
);

export default Routes;
