import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './factory.reducer';
import { IFactory } from 'app/shared/model/factory.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IFactoryUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const FactoryUpdate = (props: IFactoryUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { factoryEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/factory');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...factoryEntity,
        ...values,
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
          <h2 id="smartfactoryApp.factory.home.createOrEditLabel" data-cy="FactoryCreateUpdateHeading">
            Create or edit a Factory
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : factoryEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="factory-id">ID</Label>
                  <AvInput id="factory-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="factory-name">
                  Name
                </Label>
                <AvField id="factory-name" data-cy="name" type="text" name="name" />
              </AvGroup>
              <AvGroup>
                <Label id="descriptionLabel" for="factory-description">
                  Description
                </Label>
                <AvField id="factory-description" data-cy="description" type="text" name="description" />
              </AvGroup>
              <AvGroup>
                <Label id="locationLabel" for="factory-location">
                  Location
                </Label>
                <AvField id="factory-location" data-cy="location" type="text" name="location" />
              </AvGroup>
              <AvGroup>
                <Label id="typeLabel" for="factory-type">
                  Type
                </Label>
                <AvField id="factory-type" data-cy="type" type="text" name="type" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/factory" replace color="info">
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
  factoryEntity: storeState.factory.entity,
  loading: storeState.factory.loading,
  updating: storeState.factory.updating,
  updateSuccess: storeState.factory.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(FactoryUpdate);
