import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IProductionPlan } from 'app/shared/model/production-plan.model';
import { getEntities as getProductionPlans } from 'app/entities/production-plan/production-plan.reducer';
import { getEntity, updateEntity, createEntity, reset } from './product-data.reducer';
import { IProductData } from 'app/shared/model/product-data.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IProductDataUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ProductDataUpdate = (props: IProductDataUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { productDataEntity, productionPlans, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/product-data');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getProductionPlans();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...productDataEntity,
        ...values,
        productionPlan: productionPlans.find(it => it.id.toString() === values.productionPlanId.toString()),
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
          <h2 id="smartfactoryApp.productData.home.createOrEditLabel" data-cy="ProductDataCreateUpdateHeading">
            Create or edit a ProductData
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : productDataEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="product-data-id">ID</Label>
                  <AvInput id="product-data-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="product-data-name">
                  Name
                </Label>
                <AvField id="product-data-name" data-cy="name" type="text" name="name" />
              </AvGroup>
              <AvGroup>
                <Label id="scrapedQtyLabel" for="product-data-scrapedQty">
                  Scraped Qty
                </Label>
                <AvField id="product-data-scrapedQty" data-cy="scrapedQty" type="string" className="form-control" name="scrapedQty" />
              </AvGroup>
              <AvGroup>
                <Label id="pendingQtyLabel" for="product-data-pendingQty">
                  Pending Qty
                </Label>
                <AvField id="product-data-pendingQty" data-cy="pendingQty" type="string" className="form-control" name="pendingQty" />
              </AvGroup>
              <AvGroup>
                <Label id="rejectedQtyLabel" for="product-data-rejectedQty">
                  Rejected Qty
                </Label>
                <AvField id="product-data-rejectedQty" data-cy="rejectedQty" type="string" className="form-control" name="rejectedQty" />
              </AvGroup>
              <AvGroup>
                <Label id="completedQtyLabel" for="product-data-completedQty">
                  Completed Qty
                </Label>
                <AvField id="product-data-completedQty" data-cy="completedQty" type="string" className="form-control" name="completedQty" />
              </AvGroup>
              <AvGroup>
                <Label for="product-data-productionPlan">Production Plan</Label>
                <AvInput
                  id="product-data-productionPlan"
                  data-cy="productionPlan"
                  type="select"
                  className="form-control"
                  name="productionPlanId"
                >
                  <option value="" key="0" />
                  {productionPlans
                    ? productionPlans.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/product-data" replace color="info">
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
  productionPlans: storeState.productionPlan.entities,
  productDataEntity: storeState.productData.entity,
  loading: storeState.productData.loading,
  updating: storeState.productData.updating,
  updateSuccess: storeState.productData.updateSuccess,
});

const mapDispatchToProps = {
  getProductionPlans,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ProductDataUpdate);
