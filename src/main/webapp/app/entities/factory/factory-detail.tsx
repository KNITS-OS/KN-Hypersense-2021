import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './factory.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IFactoryDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const FactoryDetail = (props: IFactoryDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { factoryEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="factoryDetailsHeading">Factory</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{factoryEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{factoryEntity.name}</dd>
          <dt>
            <span id="description">Description</span>
          </dt>
          <dd>{factoryEntity.description}</dd>
          <dt>
            <span id="location">Location</span>
          </dt>
          <dd>{factoryEntity.location}</dd>
          <dt>
            <span id="type">Type</span>
          </dt>
          <dd>{factoryEntity.type}</dd>
        </dl>
        <Button tag={Link} to="/factory" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/factory/${factoryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ factory }: IRootState) => ({
  factoryEntity: factory.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(FactoryDetail);
