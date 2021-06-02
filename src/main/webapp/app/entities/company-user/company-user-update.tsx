import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IUserProfile } from 'app/shared/model/user-profile.model';
import { getEntities as getUserProfiles } from 'app/entities/user-profile/user-profile.reducer';
import { IBusinessUnit } from 'app/shared/model/business-unit.model';
import { getEntities as getBusinessUnits } from 'app/entities/business-unit/business-unit.reducer';
import { getEntity, updateEntity, createEntity, reset } from './company-user.reducer';
import { ICompanyUser } from 'app/shared/model/company-user.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICompanyUserUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CompanyUserUpdate = (props: ICompanyUserUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { companyUserEntity, userProfiles, businessUnits, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/company-user');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getUserProfiles();
    props.getBusinessUnits();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...companyUserEntity,
        ...values,
        userProfile: userProfiles.find(it => it.id.toString() === values.userProfileId.toString()),
        businessUnit: businessUnits.find(it => it.id.toString() === values.businessUnitId.toString()),
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="smartfactoryApp.companyUser.home.createOrEditLabel" data-cy="CompanyUserCreateUpdateHeading">
            Create or edit a CompanyUser
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : companyUserEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="company-user-id">ID</Label>
                  <AvInput id="company-user-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="usersUuidLabel" for="company-user-usersUuid">
                  Users Uuid
                </Label>
                <AvField id="company-user-usersUuid" data-cy="usersUuid" type="text" name="usersUuid" />
              </AvGroup>
              <AvGroup>
                <Label for="company-user-userProfile">User Profile</Label>
                <AvInput id="company-user-userProfile" data-cy="userProfile" type="select" className="form-control" name="userProfileId">
                  <option value="" key="0" />
                  {userProfiles
                    ? userProfiles.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="company-user-businessUnit">Business Unit</Label>
                <AvInput id="company-user-businessUnit" data-cy="businessUnit" type="select" className="form-control" name="businessUnitId">
                  <option value="" key="0" />
                  {businessUnits
                    ? businessUnits.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/company-user" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  userProfiles: storeState.userProfile.entities,
  businessUnits: storeState.businessUnit.entities,
  companyUserEntity: storeState.companyUser.entity,
  loading: storeState.companyUser.loading,
  updating: storeState.companyUser.updating,
  updateSuccess: storeState.companyUser.updateSuccess,
});

const mapDispatchToProps = {
  getUserProfiles,
  getBusinessUnits,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CompanyUserUpdate);
