import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IProductionLine } from 'app/shared/model/production-line.model';
import { getEntities as getProductionLines } from 'app/entities/production-line/production-line.reducer';
import { getEntity, updateEntity, createEntity, reset } from './things.reducer';
import { IThings } from 'app/shared/model/things.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IThingsUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ThingsUpdate = (props: IThingsUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { thingsEntity, productionLines, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/things');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getProductionLines();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...thingsEntity,
        ...values,
        productionLine: productionLines.find(it => it.id.toString() === values.productionLineId.toString()),
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
          <h2 id="smartfactoryApp.things.home.createOrEditLabel" data-cy="ThingsCreateUpdateHeading">
            Create or edit a Things
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : thingsEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="things-id">ID</Label>
                  <AvInput id="things-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="thingUuidLabel" for="things-thingUuid">
                  Thing Uuid
                </Label>
                <AvField id="things-thingUuid" data-cy="thingUuid" type="text" name="thingUuid" />
              </AvGroup>
              <AvGroup>
                <Label for="things-productionLine">Production Line</Label>
                <AvInput id="things-productionLine" data-cy="productionLine" type="select" className="form-control" name="productionLineId">
                  <option value="" key="0" />
                  {productionLines
                    ? productionLines.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/things" replace color="info">
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
  productionLines: storeState.productionLine.entities,
  thingsEntity: storeState.things.entity,
  loading: storeState.things.loading,
  updating: storeState.things.updating,
  updateSuccess: storeState.things.updateSuccess,
});

const mapDispatchToProps = {
  getProductionLines,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ThingsUpdate);
