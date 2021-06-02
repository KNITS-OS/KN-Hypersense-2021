import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './production-plan.reducer';
import { IProductionPlan } from 'app/shared/model/production-plan.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IProductionPlanUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ProductionPlanUpdate = (props: IProductionPlanUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { productionPlanEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/production-plan');
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
    values.dueDate = convertDateTimeToServer(values.dueDate);

    if (errors.length === 0) {
      const entity = {
        ...productionPlanEntity,
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
          <h2 id="smartfactoryApp.productionPlan.home.createOrEditLabel" data-cy="ProductionPlanCreateUpdateHeading">
            Create or edit a ProductionPlan
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : productionPlanEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="production-plan-id">ID</Label>
                  <AvInput id="production-plan-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="dueDateLabel" for="production-plan-dueDate">
                  Due Date
                </Label>
                <AvInput
                  id="production-plan-dueDate"
                  data-cy="dueDate"
                  type="datetime-local"
                  className="form-control"
                  name="dueDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.productionPlanEntity.dueDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="qtyLabel" for="production-plan-qty">
                  Qty
                </Label>
                <AvField id="production-plan-qty" data-cy="qty" type="string" className="form-control" name="qty" />
              </AvGroup>
              <AvGroup>
                <Label id="nameLabel" for="production-plan-name">
                  Name
                </Label>
                <AvField id="production-plan-name" data-cy="name" type="text" name="name" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/production-plan" replace color="info">
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
  productionPlanEntity: storeState.productionPlan.entity,
  loading: storeState.productionPlan.loading,
  updating: storeState.productionPlan.updating,
  updateSuccess: storeState.productionPlan.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ProductionPlanUpdate);
