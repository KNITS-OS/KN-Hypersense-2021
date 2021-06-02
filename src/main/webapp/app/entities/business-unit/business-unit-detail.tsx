import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './business-unit.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IBusinessUnitDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const BusinessUnitDetail = (props: IBusinessUnitDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { businessUnitEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="businessUnitDetailsHeading">BusinessUnit</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{businessUnitEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{businessUnitEntity.name}</dd>
          <dt>
            <span id="description">Description</span>
          </dt>
          <dd>{businessUnitEntity.description}</dd>
          <dt>Factory</dt>
          <dd>{businessUnitEntity.factory ? businessUnitEntity.factory.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/business-unit" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/business-unit/${businessUnitEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ businessUnit }: IRootState) => ({
  businessUnitEntity: businessUnit.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(BusinessUnitDetail);
