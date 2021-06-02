import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './production-plan.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IProductionPlanDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ProductionPlanDetail = (props: IProductionPlanDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { productionPlanEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="productionPlanDetailsHeading">ProductionPlan</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{productionPlanEntity.id}</dd>
          <dt>
            <span id="dueDate">Due Date</span>
          </dt>
          <dd>
            {productionPlanEntity.dueDate ? <TextFormat value={productionPlanEntity.dueDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="qty">Qty</span>
          </dt>
          <dd>{productionPlanEntity.qty}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{productionPlanEntity.name}</dd>
        </dl>
        <Button tag={Link} to="/production-plan" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/production-plan/${productionPlanEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ productionPlan }: IRootState) => ({
  productionPlanEntity: productionPlan.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ProductionPlanDetail);
