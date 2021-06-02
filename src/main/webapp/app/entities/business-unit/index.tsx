import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import BusinessUnit from './business-unit';
import BusinessUnitDetail from './business-unit-detail';
import BusinessUnitUpdate from './business-unit-update';
import BusinessUnitDeleteDialog from './business-unit-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BusinessUnitUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BusinessUnitUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BusinessUnitDetail} />
      <ErrorBoundaryRoute path={match.url} component={BusinessUnit} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={BusinessUnitDeleteDialog} />
  </>
);

export default Routes;
