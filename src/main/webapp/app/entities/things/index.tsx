import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Things from './things';
import ThingsDetail from './things-detail';
import ThingsUpdate from './things-update';
import ThingsDeleteDialog from './things-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ThingsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ThingsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ThingsDetail} />
      <ErrorBoundaryRoute path={match.url} component={Things} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ThingsDeleteDialog} />
  </>
);

export default Routes;
