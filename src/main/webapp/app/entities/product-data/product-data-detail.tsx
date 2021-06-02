import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './product-data.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IProductDataDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ProductDataDetail = (props: IProductDataDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { productDataEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="productDataDetailsHeading">ProductData</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{productDataEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{productDataEntity.name}</dd>
          <dt>
            <span id="scrapedQty">Scraped Qty</span>
          </dt>
          <dd>{productDataEntity.scrapedQty}</dd>
          <dt>
            <span id="pendingQty">Pending Qty</span>
          </dt>
          <dd>{productDataEntity.pendingQty}</dd>
          <dt>
            <span id="rejectedQty">Rejected Qty</span>
          </dt>
          <dd>{productDataEntity.rejectedQty}</dd>
          <dt>
            <span id="completedQty">Completed Qty</span>
          </dt>
          <dd>{productDataEntity.completedQty}</dd>
          <dt>Production Plan</dt>
          <dd>{productDataEntity.productionPlan ? productDataEntity.productionPlan.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/product-data" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/product-data/${productDataEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ productData }: IRootState) => ({
  productDataEntity: productData.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ProductDataDetail);
