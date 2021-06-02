import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import factory, {
  FactoryState
} from 'app/entities/factory/factory.reducer';
// prettier-ignore
import businessUnit, {
  BusinessUnitState
} from 'app/entities/business-unit/business-unit.reducer';
// prettier-ignore
import productionLine, {
  ProductionLineState
} from 'app/entities/production-line/production-line.reducer';
// prettier-ignore
import productionLineGroup, {
  ProductionLineGroupState
} from 'app/entities/production-line-group/production-line-group.reducer';
// prettier-ignore
import metric, {
  MetricState
} from 'app/entities/metric/metric.reducer';
// prettier-ignore
import metricData, {
  MetricDataState
} from 'app/entities/metric-data/metric-data.reducer';
// prettier-ignore
import companyUser, {
  CompanyUserState
} from 'app/entities/company-user/company-user.reducer';
// prettier-ignore
import userProfile, {
  UserProfileState
} from 'app/entities/user-profile/user-profile.reducer';
// prettier-ignore
import locationData, {
  LocationDataState
} from 'app/entities/location-data/location-data.reducer';
// prettier-ignore
import things, {
  ThingsState
} from 'app/entities/things/things.reducer';
// prettier-ignore
import state, {
  StateState
} from 'app/entities/state/state.reducer';
// prettier-ignore
import status, {
  StatusState
} from 'app/entities/status/status.reducer';
// prettier-ignore
import productData, {
  ProductDataState
} from 'app/entities/product-data/product-data.reducer';
// prettier-ignore
import productionPlan, {
  ProductionPlanState
} from 'app/entities/production-plan/production-plan.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly factory: FactoryState;
  readonly businessUnit: BusinessUnitState;
  readonly productionLine: ProductionLineState;
  readonly productionLineGroup: ProductionLineGroupState;
  readonly metric: MetricState;
  readonly metricData: MetricDataState;
  readonly companyUser: CompanyUserState;
  readonly userProfile: UserProfileState;
  readonly locationData: LocationDataState;
  readonly things: ThingsState;
  readonly state: StateState;
  readonly status: StatusState;
  readonly productData: ProductDataState;
  readonly productionPlan: ProductionPlanState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  factory,
  businessUnit,
  productionLine,
  productionLineGroup,
  metric,
  metricData,
  companyUser,
  userProfile,
  locationData,
  things,
  state,
  status,
  productData,
  productionPlan,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar,
});

export default rootReducer;
