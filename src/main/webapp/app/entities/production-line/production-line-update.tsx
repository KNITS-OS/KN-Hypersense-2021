import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ILocationData } from 'app/shared/model/location-data.model';
import { getEntities as getLocationData } from 'app/entities/location-data/location-data.reducer';
import { IProductionLineGroup } from 'app/shared/model/production-line-group.model';
import { getEntities as getProductionLineGroups } from 'app/entities/production-line-group/production-line-group.reducer';
import { getEntity, updateEntity, createEntity, reset } from './production-line.reducer';
import { IProductionLine } from 'app/shared/model/production-line.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IProductionLineUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ProductionLineUpdate = (props: IProductionLineUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { productionLineEntity, locationData, productionLineGroups, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/production-line');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getLocationData();
    props.getProductionLineGroups();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...productionLineEntity,
        ...values,
        locationData: locationData.find(it => it.id.toString() === values.locationDataId.toString()),
        productionLineGroup: productionLineGroups.find(it => it.id.toString() === values.productionLineGroupId.toString()),
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
          <h2 id="smartfactoryApp.productionLine.home.createOrEditLabel" data-cy="ProductionLineCreateUpdateHeading">
            Create or edit a ProductionLine
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : productionLineEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="production-line-id">ID</Label>
                  <AvInput id="production-line-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="production-line-name">
                  Name
                </Label>
                <AvField id="production-line-name" data-cy="name" type="text" name="name" />
              </AvGroup>
              <AvGroup>
                <Label id="descriptionLabel" for="production-line-description">
                  Description
                </Label>
                <AvField id="production-line-description" data-cy="description" type="text" name="description" />
              </AvGroup>
              <AvGroup>
                <Label for="production-line-locationData">Location Data</Label>
                <AvInput
                  id="production-line-locationData"
                  data-cy="locationData"
                  type="select"
                  className="form-control"
                  name="locationDataId"
                >
                  <option value="" key="0" />
                  {locationData
                    ? locationData.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="production-line-productionLineGroup">Production Line Group</Label>
                <AvInput
                  id="production-line-productionLineGroup"
                  data-cy="productionLineGroup"
                  type="select"
                  className="form-control"
                  name="productionLineGroupId"
                >
                  <option value="" key="0" />
                  {productionLineGroups
                    ? productionLineGroups.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/production-line" replace color="info">
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
  locationData: storeState.locationData.entities,
  productionLineGroups: storeState.productionLineGroup.entities,
  productionLineEntity: storeState.productionLine.entity,
  loading: storeState.productionLine.loading,
  updating: storeState.productionLine.updating,
  updateSuccess: storeState.productionLine.updateSuccess,
});

const mapDispatchToProps = {
  getLocationData,
  getProductionLineGroups,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ProductionLineUpdate);
