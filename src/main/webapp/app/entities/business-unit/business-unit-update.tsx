import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IFactory } from 'app/shared/model/factory.model';
import { getEntities as getFactories } from 'app/entities/factory/factory.reducer';
import { getEntity, updateEntity, createEntity, reset } from './business-unit.reducer';
import { IBusinessUnit } from 'app/shared/model/business-unit.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IBusinessUnitUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const BusinessUnitUpdate = (props: IBusinessUnitUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { businessUnitEntity, factories, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/business-unit');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getFactories();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...businessUnitEntity,
        ...values,
        factory: factories.find(it => it.id.toString() === values.factoryId.toString()),
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
          <h2 id="smartfactoryApp.businessUnit.home.createOrEditLabel" data-cy="BusinessUnitCreateUpdateHeading">
            Create or edit a BusinessUnit
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : businessUnitEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="business-unit-id">ID</Label>
                  <AvInput id="business-unit-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="business-unit-name">
                  Name
                </Label>
                <AvField id="business-unit-name" data-cy="name" type="text" name="name" />
              </AvGroup>
              <AvGroup>
                <Label id="descriptionLabel" for="business-unit-description">
                  Description
                </Label>
                <AvField id="business-unit-description" data-cy="description" type="text" name="description" />
              </AvGroup>
              <AvGroup>
                <Label for="business-unit-factory">Factory</Label>
                <AvInput id="business-unit-factory" data-cy="factory" type="select" className="form-control" name="factoryId">
                  <option value="" key="0" />
                  {factories
                    ? factories.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/business-unit" replace color="info">
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
  factories: storeState.factory.entities,
  businessUnitEntity: storeState.businessUnit.entity,
  loading: storeState.businessUnit.loading,
  updating: storeState.businessUnit.updating,
  updateSuccess: storeState.businessUnit.updateSuccess,
});

const mapDispatchToProps = {
  getFactories,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(BusinessUnitUpdate);
