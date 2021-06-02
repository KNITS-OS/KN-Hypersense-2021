import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Factory from './factory';
import BusinessUnit from './business-unit';
import ProductionLine from './production-line';
import ProductionLineGroup from './production-line-group';
import Metric from './metric';
import MetricData from './metric-data';
import CompanyUser from './company-user';
import UserProfile from './user-profile';
import LocationData from './location-data';
import Things from './things';
import State from './state';
import Status from './status';
import ProductData from './product-data';
import ProductionPlan from './production-plan';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}factory`} component={Factory} />
      <ErrorBoundaryRoute path={`${match.url}business-unit`} component={BusinessUnit} />
      <ErrorBoundaryRoute path={`${match.url}production-line`} component={ProductionLine} />
      <ErrorBoundaryRoute path={`${match.url}production-line-group`} component={ProductionLineGroup} />
      <ErrorBoundaryRoute path={`${match.url}metric`} component={Metric} />
      <ErrorBoundaryRoute path={`${match.url}metric-data`} component={MetricData} />
      <ErrorBoundaryRoute path={`${match.url}company-user`} component={CompanyUser} />
      <ErrorBoundaryRoute path={`${match.url}user-profile`} component={UserProfile} />
      <ErrorBoundaryRoute path={`${match.url}location-data`} component={LocationData} />
      <ErrorBoundaryRoute path={`${match.url}things`} component={Things} />
      <ErrorBoundaryRoute path={`${match.url}state`} component={State} />
      <ErrorBoundaryRoute path={`${match.url}status`} component={Status} />
      <ErrorBoundaryRoute path={`${match.url}product-data`} component={ProductData} />
      <ErrorBoundaryRoute path={`${match.url}production-plan`} component={ProductionPlan} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
