import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ProductionPlan from './production-plan';
import ProductionPlanDetail from './production-plan-detail';
import ProductionPlanUpdate from './production-plan-update';
import ProductionPlanDeleteDialog from './production-plan-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ProductionPlanUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ProductionPlanUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ProductionPlanDetail} />
      <ErrorBoundaryRoute path={match.url} component={ProductionPlan} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ProductionPlanDeleteDialog} />
  </>
);

export default Routes;
