import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import LocationData from './location-data';
import LocationDataDetail from './location-data-detail';
import LocationDataUpdate from './location-data-update';
import LocationDataDeleteDialog from './location-data-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={LocationDataUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={LocationDataUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={LocationDataDetail} />
      <ErrorBoundaryRoute path={match.url} component={LocationData} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={LocationDataDeleteDialog} />
  </>
);

export default Routes;
