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
import { getEntity, updateEntity, createEntity, reset } from './production-line-group.reducer';
import { IProductionLineGroup } from 'app/shared/model/production-line-group.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IProductionLineGroupUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ProductionLineGroupUpdate = (props: IProductionLineGroupUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { productionLineGroupEntity, factories, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/production-line-group');
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
        ...productionLineGroupEntity,
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
          <h2 id="smartfactoryApp.productionLineGroup.home.createOrEditLabel" data-cy="ProductionLineGroupCreateUpdateHeading">
            Create or edit a ProductionLineGroup
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : productionLineGroupEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="production-line-group-id">ID</Label>
                  <AvInput id="production-line-group-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="production-line-group-name">
                  Name
                </Label>
                <AvField id="production-line-group-name" data-cy="name" type="text" name="name" />
              </AvGroup>
              <AvGroup>
                <Label id="descriptionLabel" for="production-line-group-description">
                  Description
                </Label>
                <AvField id="production-line-group-description" data-cy="description" type="text" name="description" />
              </AvGroup>
              <AvGroup>
                <Label for="production-line-group-factory">Factory</Label>
                <AvInput id="production-line-group-factory" data-cy="factory" type="select" className="form-control" name="factoryId">
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
              <Button tag={Link} id="cancel-save" to="/production-line-group" replace color="info">
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
  productionLineGroupEntity: storeState.productionLineGroup.entity,
  loading: storeState.productionLineGroup.loading,
  updating: storeState.productionLineGroup.updating,
  updateSuccess: storeState.productionLineGroup.updateSuccess,
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

export default connect(mapStateToProps, mapDispatchToProps)(ProductionLineGroupUpdate);
