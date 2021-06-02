import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './production-line.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IProductionLineDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ProductionLineDetail = (props: IProductionLineDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { productionLineEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="productionLineDetailsHeading">ProductionLine</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{productionLineEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{productionLineEntity.name}</dd>
          <dt>
            <span id="description">Description</span>
          </dt>
          <dd>{productionLineEntity.description}</dd>
          <dt>Location Data</dt>
          <dd>{productionLineEntity.locationData ? productionLineEntity.locationData.id : ''}</dd>
          <dt>Production Line Group</dt>
          <dd>{productionLineEntity.productionLineGroup ? productionLineEntity.productionLineGroup.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/production-line" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/production-line/${productionLineEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ productionLine }: IRootState) => ({
  productionLineEntity: productionLine.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ProductionLineDetail);
