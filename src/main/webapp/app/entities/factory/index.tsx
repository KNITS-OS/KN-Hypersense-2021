import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Factory from './factory';
import FactoryDetail from './factory-detail';
import FactoryUpdate from './factory-update';
import FactoryDeleteDialog from './factory-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={FactoryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={FactoryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={FactoryDetail} />
      <ErrorBoundaryRoute path={match.url} component={Factory} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={FactoryDeleteDialog} />
  </>
);

export default Routes;
