import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './production-line-group.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IProductionLineGroupDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ProductionLineGroupDetail = (props: IProductionLineGroupDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { productionLineGroupEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="productionLineGroupDetailsHeading">ProductionLineGroup</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{productionLineGroupEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{productionLineGroupEntity.name}</dd>
          <dt>
            <span id="description">Description</span>
          </dt>
          <dd>{productionLineGroupEntity.description}</dd>
          <dt>Factory</dt>
          <dd>{productionLineGroupEntity.factory ? productionLineGroupEntity.factory.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/production-line-group" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/production-line-group/${productionLineGroupEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ productionLineGroup }: IRootState) => ({
  productionLineGroupEntity: productionLineGroup.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ProductionLineGroupDetail);
