import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CompanyUser from './company-user';
import CompanyUserDetail from './company-user-detail';
import CompanyUserUpdate from './company-user-update';
import CompanyUserDeleteDialog from './company-user-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CompanyUserUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CompanyUserUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CompanyUserDetail} />
      <ErrorBoundaryRoute path={match.url} component={CompanyUser} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CompanyUserDeleteDialog} />
  </>
);

export default Routes;
